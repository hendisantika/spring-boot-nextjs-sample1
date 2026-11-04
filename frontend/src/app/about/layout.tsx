import Link from 'next/link'

export default function AboutLayout({
                                        children,
                                    }: Readonly<{
    children: React.ReactNode
}>) {
    return (
        <div className="mx-auto max-w-3xl">
            <p className="eyebrow">About</p>
            <h1 className="mt-2 font-display text-4xl font-semibold">Your corner of the board</h1>
            <nav className="mt-6 flex gap-2 text-sm">
                <Link href="/about" className="btn-ghost px-4 py-1.5">Profile</Link>
                <Link href="/about/vision" className="btn-ghost px-4 py-1.5">Vision</Link>
            </nav>
            <div className="mt-8">{children}</div>
        </div>
    )
}
