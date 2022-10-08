export class VerbSearchRequest {
    searchType :string = "";
    searchInput :string = "";
    sortOrder :string = "";
    selectedLetter :string = "";
    pageSize:number = 10;
    currentPage:number = 0;
    learningStatus:string = "";
    excludedChildren:boolean = false;
}
