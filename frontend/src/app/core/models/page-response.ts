export interface PageResponse<T> {
    content: ReadonlyArray<T>;
    pageable: {
        pageNumber: number;
        pageSize: number;
    }
    totalElements: number;
    totalPages: number;
    last: boolean;
    first: boolean;
}