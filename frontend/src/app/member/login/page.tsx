'use client'

import {Notice} from '@/components/States'
import type {Member, RsData} from '@/types'
import api from '@/utils/api'
import {errorMessage} from '@/utils/format'
import {useMutation, useQueryClient} from '@tanstack/react-query'
import Link from 'next/link'
import {type ChangeEvent, type FormEvent, useState} from 'react'

export default function Login() {
    const queryClient = useQueryClient()
    const [user, setUser] = useState({username: '', password: ''})

    const login = useMutation({
        mutationFn: async (payload: typeof user) => {
            const response = await api.post<RsData<{ memberDto: Member }>>('/members/login', payload)
            return response.data.data.memberDto
        },
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['me']}),
    })

    const logout = useMutation({
        mutationFn: () => api.post('/members/logout'),
        onSuccess: () => {
            login.reset()
            queryClient.removeQueries({queryKey: ['me']})
        },
    })

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        setUser({...user, [name]: value})
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()
        logout.reset()
        login.mutate(user)
    }

    return (
        <div className="mx-auto max-w-md">
            <div className="text-center">
                <p className="eyebrow">Members</p>
                <h1 className="mt-2 font-display text-4xl font-semibold">Welcome back</h1>
                <p className="mt-2 text-sm text-stone-500">Log in to publish posts on the board.</p>
            </div>

            <div className="card mt-8 p-6 sm:p-8">
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="username" className="field-label">Username</label>
                        <input
                            id="username"
                            name="username"
                            type="text"
                            autoComplete="username"
                            required
                            value={user.username}
                            onChange={handleChange}
                            placeholder="user1"
                            className="field"
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="field-label">Password</label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            autoComplete="current-password"
                            required
                            value={user.password}
                            onChange={handleChange}
                            placeholder="••••"
                            className="field"
                        />
                    </div>

                    {login.isError && (
                        <Notice title="Login failed">
                            {errorMessage(login.error, 'Check your username and password.')}
                        </Notice>
                    )}
                    {login.isSuccess && (
                        <Notice tone="success" title={`Signed in as ${login.data.username}`}>
                            <Link href="/article" className="font-medium underline">Go to the board →</Link>
                        </Notice>
                    )}
                    {logout.isSuccess && <Notice tone="info" title="You have been logged out."/>}

                    <button type="submit" disabled={login.isPending} className="btn-primary w-full">
                        {login.isPending ? 'Signing in…' : 'Log in'}
                    </button>
                </form>

                <div className="mt-6 flex items-center justify-between border-t border-stone-100 pt-5 text-sm">
                    <span className="text-stone-500">Demo: <code
                        className="rounded bg-stone-100 px-1.5 py-0.5">user1</code> / <code
                        className="rounded bg-stone-100 px-1.5 py-0.5">1234</code></span>
                    <button
                        type="button"
                        onClick={() => logout.mutate()}
                        disabled={logout.isPending}
                        className="font-medium text-stone-600 hover:text-accent-600 disabled:opacity-50"
                    >
                        Log out
                    </button>
                </div>
            </div>
        </div>
    )
}
