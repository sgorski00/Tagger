export interface PageResponse<T> {
    content: readonly T[];
    pageable: {
        pageNumber: number;
        pageSize: number;
    }
    totalElements: number;
    totalPages: number;
    last: boolean;
    first: boolean;
}