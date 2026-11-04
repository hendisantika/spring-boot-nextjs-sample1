'use client'

import Link from 'next/link'
import {usePathname} from 'next/navigation'

const links = [
    {href: '/', label: 'Home'},
    {href: '/about', label: 'About'},
    {href: '/article', label: 'Board'},
]

export default function Navbar() {
    const pathname = usePathname()

    const isActive = (href: string) =>
        href === '/' ? pathname === '/' : pathname.startsWith(href)

    return (
        <header className="sticky top-0 z-20 border-b border-stone-200/70 bg-paper/85 backdrop-blur">
            <nav className="mx-auto flex h-16 max-w-5xl items-center justify-between gap-4 px-4 sm:px-6">
                <Link href="/" className="flex items-center gap-2.5">
                    <span
                        className="grid h-9 w-9 place-items-center rounded-xl bg-ink font-display text-lg font-semibold text-paper">
                        B
                    </span>
                    <span className="font-display text-xl font-semibold tracking-tight">Bulletin</span>
                </Link>

                <div className="flex items-center gap-1 sm:gap-2">
                    <ul className="flex items-center gap-1">
                        {links.map((link) => (
                            <li key={link.href}>
                                <Link
                                    href={link.href}
                                    aria-current={isActive(link.href) ? 'page' : undefined}
                                    className={`rounded-full px-3 py-2 text-sm font-medium transition sm:px-4 ${
                                        isActive(link.href)
                                            ? 'bg-stone-900/5 text-ink'
                                            : 'text-stone-500 hover:text-ink'
                                    }`}
                                >
                                    {link.label}
                                </Link>
                            </li>
                        ))}
                    </ul>
                    <Link href="/member/login" className="btn-primary hidden px-4 py-2 sm:inline-flex">
                        Log in
                    </Link>
                </div>
            </nav>
        </header>
    )
}
