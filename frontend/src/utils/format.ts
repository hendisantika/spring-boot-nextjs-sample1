import {isAxiosError} from 'axios'

const dateFormatter = new Intl.DateTimeFormat('en-GB', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
})

export function formatDate(value?: string): string {
    if (!value) return '-'
    const date = new Date(value)
    return Number.isNaN(date.getTime()) ? value : dateFormatter.format(date)
}

export function errorMessage(error: unknown, fallback = 'Something went wrong. Please try again.'): string {
    if (isAxiosError(error)) {
        if (!error.response) return 'Cannot reach the API server. Is the backend running on port 8080?'
        const data = error.response.data as { msg?: string } | undefined
        if (data?.msg) return data.msg
    }
    return fallback
}
