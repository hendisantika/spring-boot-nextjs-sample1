import Link from 'next/link'

const features = [
    {
        title: 'Spring Boot 4 API',
        body: 'REST endpoints on JDK 25 with Spring Data JPA, validation and a consistent RsData response envelope.',
    },
    {
        title: 'Stateless JWT auth',
        body: 'Access and refresh tokens are issued as HTTP-only cookies and verified by a custom security filter.',
    },
    {
        title: 'Next.js + TanStack Query',
        body: 'App Router pages fetch and cache data with React Query, then invalidate it after every mutation.',
    },
]

export default function Home() {
    return (
        <div className="space-y-16">
            <section className="relative overflow-hidden rounded-3xl bg-ink px-6 py-14 text-paper sm:px-12 sm:py-20">
                <div
                    aria-hidden="true"
                    className="pointer-events-none absolute -right-24 -top-24 h-72 w-72 rounded-full bg-accent-600/40 blur-3xl"
                />
                <p className="eyebrow text-accent-100">Full-stack sample</p>
                <h1 className="mt-4 max-w-2xl text-balance font-display text-4xl font-semibold leading-tight sm:text-6xl">
                    A calm little board for sharing notes.
                </h1>
                <p className="mt-5 max-w-xl text-base text-stone-300 sm:text-lg">
                    Post updates, read what others wrote, and keep it tidy. Powered by a Spring Boot backend and a
                    Next.js frontend.
                </p>
                <div className="mt-8 flex flex-wrap gap-3">
                    <Link href="/article" className="btn-primary">
                        Open the board →
                    </Link>
                    <Link
                        href="/member/login"
                        className="btn border border-stone-600 text-paper hover:border-stone-400 hover:bg-white/5"
                    >
                        Log in
                    </Link>
                </div>
            </section>

            <section>
                <p className="eyebrow">What&apos;s inside</p>
                <div className="mt-5 grid gap-4 sm:grid-cols-3">
                    {features.map((feature, i) => (
                        <article key={feature.title} className="card p-6">
                            <span className="font-display text-sm font-semibold text-accent-600">0{i + 1}</span>
                            <h2 className="mt-3 font-display text-xl font-semibold">{feature.title}</h2>
                            <p className="mt-2 text-sm leading-relaxed text-stone-600">{feature.body}</p>
                        </article>
                    ))}
                </div>
            </section>
        </div>
    )
}
