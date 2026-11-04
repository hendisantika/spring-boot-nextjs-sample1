export type RsData<T> = {
    resultCode: string
    msg: string
    data: T
}

export type Article = {
    id: number
    subject: string
    content?: string
    author: string
    createdDate: string
    modifiedDate?: string
}

export type Member = {
    id: number
    username: string
    createdDate: string
    modifiedDate: string
}
