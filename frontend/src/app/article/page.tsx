'use client'

import {Avatar, EmptyState, Notice, Skeleton} from '@/components/States'
import type {Article, RsData} from '@/types'
import api from '@/utils/api'
import {errorMessage, formatDate} from '@/utils/format'
import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query'
import Link from 'next/link'
import {type ChangeEvent, type FormEvent, useState} from 'react'

const getArticles = async () => {
    const response = await api.get<RsData<{ articles: Article[] }>>('/articles')
    return [...response.data.data.articles].sort((a, b) => b.id - a.id)
}

export default function ArticleBoard() {
    const queryClient = useQueryClient()
    const {isPending, error, data} = useQuery({queryKey: ['articles'], queryFn: getArticles})

    const remove = useMutation({
        mutationFn: (id: number) => api.delete(`/articles/${id}`),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['articles']}),
    })

    return (
        <div className="grid grid-cols-1 gap-10 lg:grid-cols-[minmax(0,1fr)_320px] lg:items-start">
            <section className="min-w-0">
                <div className="mb-6 flex items-end justify-between gap-4">
                    <div>
                        <p className="eyebrow">Notice board</p>
                        <h1 className="mt-2 font-display text-3xl font-semibold sm:text-4xl">Latest posts</h1>
                    </div>
                    {data && (
                        <span className="rounded-full bg-stone-900/5 px-3 py-1 text-xs font-medium text-stone-600">
                            {data.length} {data.length === 1 ? 'post' : 'posts'}
                        </span>
                    )}
                </div>

                {isPending && <Skeleton rows={4}/>}

                {error && <Notice title="Could not load posts">{errorMessage(error)}</Notice>}

                {remove.error && (
                    <div className="mb-4">
                        <Notice title="Delete failed">{errorMessage(remove.error)}</Notice>
                    </div>
                )}

                {data && data.length === 0 && (
                    <EmptyState title="No posts yet" description="Be the first to share something with the board."/>
                )}

                {data && data.length > 0 && (
                    <ul className="space-y-3">
                        {data.map((article) => (
                            <li key={article.id}
                                className="card group flex items-center gap-4 p-4 transition hover:-translate-y-0.5 hover:border-stone-300 sm:p-5">
                                <Avatar name={article.author}/>
                                <div className="min-w-0 flex-1">
                                    <Link
                                        href={`/article/${article.id}`}
                                        className="block truncate font-display text-lg font-semibold text-ink group-hover:text-accent-600"
                                    >
                                        {article.subject}
                                    </Link>
                                    <p className="mt-0.5 text-xs text-stone-500">
                                        <span className="font-medium text-stone-700">{article.author}</span>
                                        {' · '}
                                        <time dateTime={article.createdDate}>{formatDate(article.createdDate)}</time>
                                        {' · '}#{article.id}
                                    </p>
                                </div>
                                <button
                                    type="button"
                                    onClick={() => remove.mutate(article.id)}
                                    disabled={remove.isPending && remove.variables === article.id}
                                    aria-label={`Delete "${article.subject}"`}
                                    className="rounded-full px-3 py-1.5 text-xs font-medium text-stone-400 transition hover:bg-red-50 hover:text-red-600 focus:outline-none focus-visible:ring-2 focus-visible:ring-red-400 disabled:opacity-50"
                                >
                                    {remove.isPending && remove.variables === article.id ? 'Deleting…' : 'Delete'}
                                </button>
                            </li>
                        ))}
                    </ul>
                )}
            </section>

            <ArticleForm/>
        </div>
    )
}

function ArticleForm() {
    const queryClient = useQueryClient()
    const [article, setArticle] = useState({subject: '', content: ''})

    const create = useMutation({
        mutationFn: (payload: typeof article) => api.post('/articles', payload),
        onSuccess: () => {
            setArticle({subject: '', content: ''})
            queryClient.invalidateQueries({queryKey: ['articles']})
        },
    })

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target
        setArticle({...article, [name]: value})
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()
        create.mutate(article)
    }

    return (
        <aside className="card p-6 lg:sticky lg:top-24">
            <h2 className="font-display text-xl font-semibold">Write a post</h2>
            <p className="mt-1 text-sm text-stone-500">You need to be logged in to publish.</p>

            <form onSubmit={handleSubmit} className="mt-5 space-y-4">
                <div>
                    <label htmlFor="subject" className="field-label">Title</label>
                    <input
                        id="subject"
                        name="subject"
                        type="text"
                        required
                        value={article.subject}
                        onChange={handleChange}
                        placeholder="What's new?"
                        className="field"
                    />
                </div>
                <div>
                    <label htmlFor="content" className="field-label">Content</label>
                    <textarea
                        id="content"
                        name="content"
                        required
                        rows={4}
                        value={article.content}
                        onChange={handleChange}
                        placeholder="Share the details…"
                        className="field resize-y"
                    />
                </div>

                {create.isError && (
                    <Notice title="Could not publish">
                        {errorMessage(create.error, 'Please log in first, then try again.')}
                    </Notice>
                )}
                {create.isSuccess && <Notice tone="success" title="Post published"/>}

                <button type="submit" disabled={create.isPending} className="btn-primary w-full">
                    {create.isPending ? 'Publishing…' : 'Publish'}
                </button>
            </form>
        </aside>
    )
}
