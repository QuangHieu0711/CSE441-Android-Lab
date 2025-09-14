# Next.js Login Application

A modern, responsive login page built with Next.js, TypeScript, and React.

## Features

- ✅ **Responsive Design**: Clean, modern login form with gradient background
- ✅ **Form Validation**: Client-side and server-side email and password validation
- ✅ **Authentication API**: RESTful API endpoint for login handling
- ✅ **Social Login Placeholders**: Facebook, Twitter, and LinkedIn login buttons
- ✅ **Remember Me**: Checkbox functionality with cookie support
- ✅ **Forgot Password**: Link to forgot password page
- ✅ **Error Handling**: User-friendly error messages for invalid credentials
- ✅ **Success Messages**: Confirmation messages for successful login
- ✅ **Accessibility**: Proper ARIA labels and semantic HTML
- ✅ **TypeScript**: Full type safety throughout the application

## Demo Credentials

For testing purposes, you can use any of these demo accounts:

- **Email:** `user@example.com` **Password:** `password123`
- **Email:** `admin@test.com` **Password:** `admin123` 
- **Email:** `demo@demo.com` **Password:** `demo123`

## Getting Started

1. **Install Dependencies:**
   ```bash
   npm install
   ```

2. **Run Development Server:**
   ```bash
   npm run dev
   ```

3. **Open your browser and navigate to:**
   ```
   http://localhost:3000
   ```

## API Endpoints

### Login API: `/api/auth/login`

**POST Request:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "rememberMe": true
}
```

**Success Response:**
```json
{
  "success": true,
  "message": "Welcome back! You are now logged in. Your session will be remembered."
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

**GET Request:** Returns demo credentials and API information

## Project Structure

```
src/
├── app/
│   ├── api/auth/login/
│   │   └── route.ts          # Login API endpoint
│   ├── forgot-password/
│   │   └── page.tsx          # Forgot password page
│   ├── login/
│   │   └── page.tsx          # Main login page
│   ├── globals.css           # Global styles
│   ├── layout.tsx            # Root layout
│   └── page.tsx              # Home page (redirects to login)
```

## Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run start` - Start production server
- `npm run lint` - Run ESLint

## Technologies Used

- **Next.js 14** - React framework with App Router
- **TypeScript** - Type safety
- **React 18** - UI library
- **CSS3** - Responsive styling with gradients and animations
- **ESLint** - Code linting

## Security Features

- Input validation on both client and server side
- Secure cookie handling for "Remember Me" functionality
- Proper error handling without exposing sensitive information
- TypeScript for type safety

## Browser Compatibility

- Modern browsers (Chrome, Firefox, Safari, Edge)
- Mobile responsive design
- Accessible with screen readers