export interface GeneralFormValue {
  item: string;
  tagsQuantity: number | null;
  platform: string;
  targetAudience: string;
  responseStyle: string;
}

export interface ClothesFormValue extends GeneralFormValue {
  color: string;
  size: string;
  material: string;
}

export interface ElectronicsFormValue extends GeneralFormValue {
  model: string;
  color: string;
  monthsOfWarranty: number | null;
}

export type FormValue = GeneralFormValue | ClothesFormValue | ElectronicsFormValue;
