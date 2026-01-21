import {GeneralGenerationRequest} from './general-generation-request';

export interface ClothesGenerationsRequest extends GeneralGenerationRequest {
  color?: string;
  size?: string;
  material?: string;
}
