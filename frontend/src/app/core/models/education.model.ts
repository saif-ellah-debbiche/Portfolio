export interface Education {
  universityName: string
  logo: string
  degree: string
  status: "completed" | "in-progress"
  startDate: string
  endDate: string // Use 'Present' for ongoing education
  description?: string
}