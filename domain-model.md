```mermaid
classDiagram
    class User {
        - id: int
        - name: string
        - email: string
        - password: string
        + set_name(name: string): void
        + set_email(email: string): void
        + set_password(password: string): void
    }

    class Project{
        - id : int
        - name : string
        - description : string
        - start_date : date
        - end_date : date
        + set_name(name: string) : void
        + set_description(description: string) : void
        + set_start_date(start_date: date) : void
        + set_end_date(end_date: date) : void
    }

    class Task{
        - id : int
        - name : string
        - description : string
        - start_date : date
        - end_date : date
        - status : string
        + set_name(name: string) : void
        + set_description(description: string) : void
        + set_start_date(start_date: date) : void
        + set_end_date(end_date: date) : void
        + set_status(status: string) : void
    }

    class Subtask {
        - id: int
        - name: string
        - description: string
        - start_date: date
        - end_date: date
        - status: string
        + set_name(name: string): void
        + set_description(description: string): void
        + set_start_date(start_date: date): void
        + set_end_date(end_date: date): void
        + set_status(status: string): void
    }

    User "1" -- "0..*" Task
    Task "1" -- "0..*" Subtask
    Project "1" -- "0..*" Task
    ```
