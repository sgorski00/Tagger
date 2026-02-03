export interface GenerationResponse {
  id: number;
  title: string;
  description: string;
  tags: readonly string[];
  createdAt: Date;
}
