'use client'

import {Avatar, Notice, Skeleton} from '@/components/States'
import type {Article, RsData} from '@/types'
import api from '@/utils/api'
import {errorMessage, formatDate} from '@/utils/format'
import {useQuery} from '@tanstack/react-query'
import Link from 'next/link'
import {useParams} from 'next/navigation'

export default function ArticleDetail() {
    const {id} = useParams<{ id: string }>()

    const {isPending, error, data} = useQuery({
        queryKey: ['article', id],
        queryFn: async () => {
            const response = await api.get<RsData<{ article: Article | null }>>(`/articles/${id}`)
            if (!response.data.data?.article) throw new Error(response.data.msg)
            return response.data.data.article
        },
    })

    return (
        <div className="mx-auto max-w-3xl">
            <Link href="/article" className="text-sm font-medium text-stone-500 hover:text-accent-600">
                ← Back to the board
            </Link>

            <div className="mt-6">
                {isPending && <Skeleton rows={1}/>}

                {error && (
                    <Notice title={`Post #${id} could not be loaded`}>
                        {errorMessage(error, error.message)}
                    </Notice>
                )}

                {data && (
                    <article className="card overflow-hidden">
                        <header className="border-b border-stone-100 px-6 py-8 sm:px-10">
                            <p className="eyebrow">Post #{data.id}</p>
                            <h1 className="mt-3 text-balance font-display text-3xl font-semibold leading-tight sm:text-5xl">
                                {data.subject}
                            </h1>
                            <div className="mt-6 flex items-center gap-3">
                                <Avatar name={data.author}/>
                                <div className="text-sm">
                                    <p className="font-medium text-ink">{data.author}</p>
                                    <p className="text-stone-500">
                                        <time dateTime={data.createdDate}>{formatDate(data.createdDate)}</time>
                                    </p>
                                </div>
                            </div>
                        </header>
                        <div className="whitespace-pre-line px-6 py-8 text-lg leading-relaxed text-stone-700 sm:px-10">
                            {data.content || <span className="italic text-stone-400">No content.</span>}
                        </div>
                        {data.modifiedDate && data.modifiedDate !== data.createdDate && (
                            <footer className="border-t border-stone-100 px-6 py-4 text-xs text-stone-500 sm:px-10">
                                Last edited {formatDate(data.modifiedDate)}
                            </footer>
                        )}
                    </article>
                )}
            </div>
        </div>
    )
}
