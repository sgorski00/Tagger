import {Language} from '../../language/language-config';

export interface FormConfig {
  tagsQuantityMin: number;
  tagsQuantityMax: number;
  monthsOfWarrantyMin: number;
  monthsOfWarrantyMax: number;
  itemNameMinLength: number;
  responseStyles: ReadonlySet<string>;
  platforms: ReadonlySet<string>;
  languages: ReadonlySet<Language>;
}
