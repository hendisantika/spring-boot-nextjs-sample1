'use client'

import {Avatar, EmptyState, Skeleton} from '@/components/States'
import type {Member, RsData} from '@/types'
import api from '@/utils/api'
import {formatDate} from '@/utils/format'
import {useQuery} from '@tanstack/react-query'

export default function About() {
    const {isPending, error, data: member} = useQuery({
        queryKey: ['me'],
        queryFn: async () => {
            const response = await api.get<RsData<{ member: Member }>>('/members/me')
            return response.data.data.member
        },
        retry: false,
    })

    if (isPending) return <Skeleton rows={1}/>

    if (error || !member) {
        return (
            <EmptyState
                title="You're not logged in"
                description="Log in to see your member profile."
                action={{href: '/member/login', label: 'Log in'}}
            />
        )
    }

    return (
        <div className="card p-6 sm:p-8">
            <div className="flex items-center gap-4">
                <Avatar name={member.username}/>
                <div>
                    <p className="font-display text-2xl font-semibold">{member.username}</p>
                    <p className="text-sm text-stone-500">Member #{member.id}</p>
                </div>
            </div>
            <dl className="mt-6 grid gap-4 border-t border-stone-100 pt-6 text-sm sm:grid-cols-2">
                <div>
                    <dt className="text-stone-500">Joined</dt>
                    <dd className="mt-1 font-medium">{formatDate(member.createdDate)}</dd>
                </div>
                <div>
                    <dt className="text-stone-500">Last updated</dt>
                    <dd className="mt-1 font-medium">{formatDate(member.modifiedDate)}</dd>
                </div>
            </dl>
        </div>
    )
}
