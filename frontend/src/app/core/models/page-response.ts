export interface PageResponse<T> {
    content: ReadonlyArray<T>;
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
    last: boolean;
    first: boolean;
}