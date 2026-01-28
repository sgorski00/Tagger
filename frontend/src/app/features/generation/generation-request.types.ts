/**
 * Base interface for all generation requests
 */
export interface BaseGenerationRequest {
  item: string;
  tagsQuantity?: number;
  platform?: string;
  targetAudience: string;
  responseStyle: string;
}

/**
 * General generation request without specific category fields
 */
export interface GeneralGenerationRequest extends BaseGenerationRequest {
  mode: 'general';
}

/**
 * Clothes-specific generation request
 */
export interface ClothesGenerationRequest extends BaseGenerationRequest {
  mode: 'clothes';
  color?: string;
  size?: string;
  material?: string;
}

/**
 * Electronics-specific generation request
 */
export interface ElectronicsGenerationRequest extends BaseGenerationRequest {
  mode: 'electronics';
  model?: string;
  color?: string;
  monthsOfWarranty?: number;
}

/**
 * Union type of all possible generation requests
 */
export type GenerationRequest =
  | GeneralGenerationRequest
  | ClothesGenerationRequest
  | ElectronicsGenerationRequest;

/**
 * Type guard to check if request is for general generation
 */
export function isGeneralRequest(
  request: GenerationRequest
): request is GeneralGenerationRequest {
  return request.mode === 'general';
}

/**
 * Type guard to check if request is for clothes generation
 */
export function isClothesRequest(
  request: GenerationRequest
): request is ClothesGenerationRequest {
  return request.mode === 'clothes';
}

/**
 * Type guard to check if request is for electronics generation
 */
export function isElectronicsRequest(
  request: GenerationRequest
): request is ElectronicsGenerationRequest {
  return request.mode === 'electronics';
}
