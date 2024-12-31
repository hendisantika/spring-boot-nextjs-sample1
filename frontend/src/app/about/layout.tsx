export default function AboutLayout({
                                        children,
                                    }: Readonly<{
    children: React.ReactNode
}>) {
    return (
        <>
            <h1>Common elements of introduction page</h1>
            {children}
        </>
    )
}
