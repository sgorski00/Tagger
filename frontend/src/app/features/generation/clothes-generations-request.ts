import {BaseGenerationRequest} from "./base-generation-request";

export interface ClothesGenerationsRequest extends BaseGenerationRequest {
  mode: 'clothes';
  color?: string;
  size?: string;
  material?: string;
}
