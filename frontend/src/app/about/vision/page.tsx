const principles = [
    {title: 'Simple', body: 'One board, clear posts, nothing in the way.'},
    {title: 'Secure', body: 'Stateless JWT cookies keep sessions out of the server.'},
    {title: 'Modern', body: 'JDK 25, Spring Boot 4 and the Next.js App Router.'},
]

export default function Vision() {
    return (
        <div className="grid gap-4 sm:grid-cols-3">
            {principles.map((p) => (
                <div key={p.title} className="card p-6">
                    <h2 className="font-display text-xl font-semibold">{p.title}</h2>
                    <p className="mt-2 text-sm leading-relaxed text-stone-600">{p.body}</p>
                </div>
            ))}
        </div>
    )
}
