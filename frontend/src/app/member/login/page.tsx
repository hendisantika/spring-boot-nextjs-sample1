'use client'

import api from '@/utils/api'
import {useState} from 'react'

export default function Login() {
    const [user, setUser] = useState({username: '', password: ''})

    const handleSubmit = async (e) => {
        e.preventDefault()
        await api.post('/members/login', user)
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setUser({...user, [name]: value})
    }

    const handleLogout = async () => {
        await api.post('/members/logout')
    }

    return (
        <>
            <h3>Login form</h3>
            <form onSubmit={handleSubmit}>
                <input type="text" name="username" onChange={handleChange}/>
                <input
                    type="password"
                    name="password"
                    onChange={handleChange}
                />
                <button type="submit">Register</button>
            </form>
            <button onClick={handleLogout}>log out</button>
        </>
    )
}
