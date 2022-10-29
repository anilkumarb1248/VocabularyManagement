import { VocabularyType } from "./vocabulary-type";

export class VocabularyHeader {
    vocabularyHeaderId : number | undefined;
    vocabularyHeader : string = "";
    vocabularyHeaderParent : number  = 0;
    vocabularyType: VocabularyType = VocabularyType.TEXT;
}
