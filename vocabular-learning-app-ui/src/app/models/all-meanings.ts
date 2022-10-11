import { MeaningTextType } from "./meaning-text-type";
import { MeaningType } from "./meaning-type";

export class AllMeanings {

    public id: number = 0;
    public title: string = "";
    public meaningType: MeaningType = MeaningType.ARTICLE;
    public text: string = "";
    public meaningTextType: MeaningTextType = MeaningTextType.TEXT;
    public script: string = "";
    public meanings: string = "";
    public createdTimeStamp: Date | undefined;
    public updatedTimeStamp: Date | undefined;
}
