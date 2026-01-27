import {BaseGenerationRequest} from "./base-generation-request";

export interface GeneralGenerationRequest extends BaseGenerationRequest{
  mode: 'general';
}
