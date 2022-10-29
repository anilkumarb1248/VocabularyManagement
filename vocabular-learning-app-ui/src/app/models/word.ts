export class Word {

    public wordId : number | undefined;
    public word: string = "";
    public phonetics: string = "";
    public synonyms: string = "";
    public antonyms: string = "";
    public notes: string = "";
    public wordMeanings:string[] = [];
    public createdTimeStamp: Date | undefined;
    public updatedTimeStamp: Date | undefined;

}
