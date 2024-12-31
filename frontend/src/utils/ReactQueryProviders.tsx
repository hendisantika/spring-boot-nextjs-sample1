'use client'
import {QueryClient, QueryClientProvider} from '@tanstack/react-query'
import {ReactQueryDevtools} from '@tanstack/react-query-devtools'
import {useState} from 'react'

export default function ReactQueryProviders({
                                                children,
                                            }: React.PropsWithChildren) {
    const [client] = useState(
        new QueryClient({
            defaultOptions: {
                queries: {
                    refetchOnWindowFocus: false, // Refetch the data when the window gets focus again
                    refetchOnMount: false, // If the data is stale, refetch it when the component mounts.
                    retry: 1, // Option to retry when API request fails (retry as many times as set value)
                },
            },
        })
    )

    return (
        <QueryClientProvider client={client}>
            {children}
            <ReactQueryDevtools initialIsOpen={false}/>
        </QueryClientProvider>
    )
}
