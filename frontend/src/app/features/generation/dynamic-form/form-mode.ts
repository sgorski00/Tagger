export const FORM_MODES = ['general', 'clothes', 'electronics'] as const;
export type FormMode = typeof FORM_MODES[number];