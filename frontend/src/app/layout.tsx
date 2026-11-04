import Navbar from '@/components/Navbar'
import ReactQueryProviders from '@/utils/ReactQueryProviders'
import type {Metadata} from 'next'
import {Fraunces, Inter} from 'next/font/google'
import './globals.css'

const inter = Inter({subsets: ['latin'], variable: '--font-sans'})
const fraunces = Fraunces({subsets: ['latin'], variable: '--font-display'})

export const metadata: Metadata = {
    title: 'Bulletin — Spring Boot × Next.js',
    description: 'A sample bulletin board built with Spring Boot 4 (JDK 25) and Next.js.',
}

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode
}>) {
    return (
        <html lang="en" className={`${inter.variable} ${fraunces.variable}`}>
        <body className="flex min-h-screen flex-col">
        <Navbar/>
        <ReactQueryProviders>
            <main className="mx-auto w-full max-w-5xl flex-1 px-4 py-10 sm:px-6 sm:py-14">{children}</main>
        </ReactQueryProviders>
        <footer className="border-t border-stone-200/70">
            <div
                className="mx-auto flex max-w-5xl flex-col gap-1 px-4 py-6 text-xs text-stone-500 sm:flex-row sm:justify-between sm:px-6">
                <p>Bulletin · Spring Boot 4 on JDK 25 + Next.js 15</p>
                <p>
                    Made by{' '}
                    <a href="https://s.id/hendisantika" className="font-medium text-stone-700 hover:text-accent-600">
                        Hendi Santika
                    </a>
                </p>
            </div>
        </footer>
        </body>
        </html>
    )
}
