import { VocabularyType } from "./vocabulary-type";

export class VocabularyHeader {
    headerId : number | undefined;
    header : string = "";
    parentId : number  = 0;
    headerType: VocabularyType = VocabularyType.TEXT;
    childHeaders ?: VocabularyHeader[];
}
