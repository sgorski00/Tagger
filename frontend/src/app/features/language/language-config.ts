export interface Language {
  code: string;
  name: string;
}
export const SUPPORTED_LANGUAGES: ReadonlySet<Language> = new Set([
  { code: 'en-US', name: 'English (US)' },
  { code: 'es-ES', name: 'Español' },
  { code: 'fr-FR', name: 'Français' },
  { code: 'de-DE', name: 'Deutsch' },
  { code: 'it-IT', name: 'Italiano' },
  { code: 'pt-PT', name: 'Português' },
  { code: 'ru-RU', name: 'Русский' },
  { code: 'ja-JP', name: '日本語' },
  { code: 'zh-CN', name: '中文' },
  { code: 'pl-PL', name: 'Polski' },
]);
