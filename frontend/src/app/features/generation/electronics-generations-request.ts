import {BaseGenerationRequest} from "./base-generation-request";

export interface ElectronicsGenerationsRequest extends BaseGenerationRequest {
  mode: 'electronics';
  model?: string;
  color?: string;
  monthsOfWarranty?: number;
}
