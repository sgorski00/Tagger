import {GeneralGenerationRequest} from './general-generation-request';

export interface ElectronicsGenerationsRequest extends GeneralGenerationRequest {
  model?: string;
  color?: string;
  monthsOfWarranty?: number;
}
