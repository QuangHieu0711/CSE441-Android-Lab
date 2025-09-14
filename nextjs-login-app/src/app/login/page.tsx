'use client'

import { useState } from 'react'

interface LoginFormData {
  email: string
  password: string
  rememberMe: boolean
}

interface LoginResponse {
  success: boolean
  message: string
}

export default function LoginPage() {
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    password: '',
    rememberMe: false
  })
  const [isLoading, setIsLoading] = useState(false)
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null)
  const [errors, setErrors] = useState<{ email?: string; password?: string }>({})

  const validateForm = (): boolean => {
    const newErrors: { email?: string; password?: string } = {}

    // Email validation
    if (!formData.email) {
      newErrors.email = 'Email is required'
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address'
    }

    // Password validation
    if (!formData.password) {
      newErrors.password = 'Password is required'
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters long'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }))

    // Clear errors when user starts typing
    if (errors[name as keyof typeof errors]) {
      setErrors(prev => ({ ...prev, [name]: undefined }))
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setMessage(null)

    if (!validateForm()) {
      return
    }

    setIsLoading(true)

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      })

      const data: LoginResponse = await response.json()

      if (data.success) {
        setMessage({ type: 'success', text: data.message })
        // Here you would typically redirect to a dashboard or home page
        // For demo purposes, we'll just show a success message
      } else {
        setMessage({ type: 'error', text: data.message })
      }
    } catch (error) {
      setMessage({ type: 'error', text: 'An error occurred. Please try again.' })
    } finally {
      setIsLoading(false)
    }
  }

  const handleSocialLogin = (provider: string) => {
    // Placeholder for social login implementation
    setMessage({ type: 'success', text: `${provider} login would be implemented here` })
  }

  return (
    <div className="login-container">
      <div className="login-header">
        <h1 className="login-title">Welcome Back</h1>
        <p className="login-subtitle">Please sign in to your account</p>
      </div>

      {message && (
        <div className={message.type === 'success' ? 'success-message' : 'error-message'}>
          {message.text}
        </div>
      )}

      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email" className="form-label">
            Email Address
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            className={`form-input ${errors.email ? 'error' : ''}`}
            placeholder="Enter your email"
            aria-describedby={errors.email ? 'email-error' : undefined}
          />
          {errors.email && (
            <span id="email-error" className="error-message" role="alert">
              {errors.email}
            </span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            className={`form-input ${errors.password ? 'error' : ''}`}
            placeholder="Enter your password"
            aria-describedby={errors.password ? 'password-error' : undefined}
          />
          {errors.password && (
            <span id="password-error" className="error-message" role="alert">
              {errors.password}
            </span>
          )}
        </div>

        <div className="checkbox-group">
          <input
            type="checkbox"
            id="rememberMe"
            name="rememberMe"
            checked={formData.rememberMe}
            onChange={handleInputChange}
            className="checkbox-input"
          />
          <label htmlFor="rememberMe" className="checkbox-label">
            Remember me
          </label>
        </div>

        <button
          type="submit"
          className="login-button"
          disabled={isLoading}
        >
          {isLoading ? 'Signing in...' : 'Sign In'}
        </button>
      </form>

      <div className="social-login">
        <div className="social-title">
          <span>Or sign in with</span>
        </div>
        <div className="social-buttons">
          <button
            type="button"
            className="social-button facebook"
            onClick={() => handleSocialLogin('Facebook')}
            aria-label="Sign in with Facebook"
          >
            📘
          </button>
          <button
            type="button"
            className="social-button twitter"
            onClick={() => handleSocialLogin('Twitter')}
            aria-label="Sign in with Twitter"
          >
            🐦
          </button>
          <button
            type="button"
            className="social-button linkedin"
            onClick={() => handleSocialLogin('LinkedIn')}
            aria-label="Sign in with LinkedIn"
          >
            💼
          </button>
        </div>
      </div>

      <div className="forgot-password">
        <a href="/forgot-password" className="forgot-password-link">
          Forgot your password?
        </a>
      </div>
    </div>
  )
}