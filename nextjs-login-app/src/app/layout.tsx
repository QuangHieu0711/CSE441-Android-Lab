import type { Metadata } from 'next'
import './globals.css'

export const metadata: Metadata = {
  title: 'Login App',
  description: 'A Next.js login application',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}