export interface FormConfig {
  tagsQuantityMin: number;
  tagsQuantityMax: number;
  monthsOfWarrantyMin: number;
  monthsOfWarrantyMax: number;
  itemNameMinLength: number;
  responseStyles: ReadonlyArray<string>;
  platforms: ReadonlyArray<string>;
}
