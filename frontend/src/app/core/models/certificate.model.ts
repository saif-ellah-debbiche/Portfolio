export interface Certification {
  providerName: string
  providerLogo: string
  title: string
  date: Date
  summary: string
  status: "completed" | "in-progress"
  credentialUrl?: string // Optional link to verify the certificate
}
