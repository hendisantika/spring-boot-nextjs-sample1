import Link from 'next/link'
import type {ReactNode} from 'react'

export function Skeleton({rows = 3}: { rows?: number }) {
    return (
        <div className="space-y-3" aria-busy="true" aria-label="Loading">
            {Array.from({length: rows}).map((_, i) => (
                <div key={i} className="card flex animate-pulse items-center gap-4 p-5">
                    <div className="h-10 w-10 rounded-full bg-stone-200"/>
                    <div className="flex-1 space-y-2">
                        <div className="h-4 w-1/3 rounded bg-stone-200"/>
                        <div className="h-3 w-1/4 rounded bg-stone-100"/>
                    </div>
                </div>
            ))}
        </div>
    )
}

export function Notice({
                           tone = 'error',
                           title,
                           children,
                       }: {
    tone?: 'error' | 'info' | 'success'
    title: string
    children?: ReactNode
}) {
    const styles = {
        error: 'border-red-200 bg-red-50 text-red-800',
        info: 'border-stone-200 bg-white text-stone-700',
        success: 'border-emerald-200 bg-emerald-50 text-emerald-800',
    }[tone]

    return (
        <div role={tone === 'error' ? 'alert' : 'status'} className={`rounded-xl border px-4 py-3 text-sm ${styles}`}>
            <p className="font-semibold">{title}</p>
            {children && <div className="mt-1 opacity-90">{children}</div>}
        </div>
    )
}

export function EmptyState({title, description, action}: {
    title: string
    description: string
    action?: { href: string; label: string }
}) {
    return (
        <div className="card flex flex-col items-center px-6 py-14 text-center">
            <div className="mb-4 grid h-12 w-12 place-items-center rounded-full bg-accent-50 text-xl text-accent-600">
                ✦
            </div>
            <h3 className="font-display text-xl font-semibold">{title}</h3>
            <p className="mt-1 max-w-sm text-sm text-stone-500">{description}</p>
            {action && (
                <Link href={action.href} className="btn-ghost mt-6">
                    {action.label}
                </Link>
            )}
        </div>
    )
}

function initials(name: string): string {
    const clean = name.replace(/[^a-z0-9]/gi, '')
    const last = clean.at(-1) ?? ''
    return (clean.charAt(0) + (/\d/.test(last) ? last : clean.charAt(1))).toUpperCase()
}

export function Avatar({name}: { name: string }) {
    return (
        <span
            aria-hidden="true"
            className="grid h-10 w-10 shrink-0 place-items-center rounded-full bg-accent-50 text-sm font-semibold uppercase text-accent-700 ring-1 ring-accent-100"
        >
            {initials(name)}
        </span>
    )
}
