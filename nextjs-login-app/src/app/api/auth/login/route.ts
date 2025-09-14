import { NextRequest, NextResponse } from 'next/server'

interface LoginRequestBody {
  email: string
  password: string
  rememberMe: boolean
}

// Placeholder user database (in a real app, this would be a proper database)
const DEMO_USERS = [
  { email: 'user@example.com', password: 'password123' },
  { email: 'admin@test.com', password: 'admin123' },
  { email: 'demo@demo.com', password: 'demo123' }
]

export async function POST(request: NextRequest) {
  try {
    const body: LoginRequestBody = await request.json()
    const { email, password, rememberMe } = body

    // Server-side validation
    if (!email || !password) {
      return NextResponse.json(
        { success: false, message: 'Email and password are required' },
        { status: 400 }
      )
    }

    // Email format validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(email)) {
      return NextResponse.json(
        { success: false, message: 'Please enter a valid email address' },
        { status: 400 }
      )
    }

    // Password length validation
    if (password.length < 6) {
      return NextResponse.json(
        { success: false, message: 'Password must be at least 6 characters long' },
        { status: 400 }
      )
    }

    // Simulate authentication delay
    await new Promise(resolve => setTimeout(resolve, 1000))

    // Check credentials against demo users
    const user = DEMO_USERS.find(
      u => u.email.toLowerCase() === email.toLowerCase() && u.password === password
    )

    if (!user) {
      return NextResponse.json(
        { success: false, message: 'Invalid email or password' },
        { status: 401 }
      )
    }

    // Successful login
    // In a real application, you would:
    // 1. Generate a JWT token or session
    // 2. Set secure cookies
    // 3. Handle "remember me" functionality
    // 4. Log the login attempt
    
    const response = NextResponse.json({
      success: true,
      message: `Welcome back! You are now logged in.${rememberMe ? ' Your session will be remembered.' : ''}`
    })

    // Set a demo cookie for remember me functionality
    if (rememberMe) {
      response.cookies.set('remember-user', email, {
        maxAge: 60 * 60 * 24 * 30, // 30 days
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'strict'
      })
    }

    return response

  } catch (error) {
    console.error('Login API error:', error)
    return NextResponse.json(
      { success: false, message: 'Internal server error' },
      { status: 500 }
    )
  }
}

// Handle GET requests to provide demo information
export async function GET() {
  return NextResponse.json({
    message: 'Login API endpoint',
    demoCredentials: [
      { email: 'user@example.com', password: 'password123' },
      { email: 'admin@test.com', password: 'admin123' },
      { email: 'demo@demo.com', password: 'demo123' }
    ],
    note: 'These are demo credentials for testing purposes'
  })
}