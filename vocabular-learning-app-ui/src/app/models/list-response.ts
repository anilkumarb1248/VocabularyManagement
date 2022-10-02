import { Status } from "./status";

export class ListResponse {

    public values: any[];
    public status: Status;
    public msg:string;
    public source:string;

    constructor(values:any[], msg:string, source:string, status: Status){
        this.values = values;
        this.msg = msg;
        this.source = source;
        this.status = status;
    }

    public getValues(): any[] {
        return this.values;
    }

    public setValues(values: any[]): void {
        this.values = values;
    }

    public getStatus(): Status {
        return this.status;
    }

    public setStatus(status: Status): void {
        this.status = status;
    }

    public getMsg(): string {
        return this.msg;
    }

    public setMsg(msg: string): void {
        this.msg = msg;
    }

    public getSource(): string {
        return this.source;
    }

    public setSource(source: string): void {
        this.source = source;
    }

    
}
