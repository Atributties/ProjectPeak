```sql
Table User {
  id int [pk]
  name varchar
  email varchar
  password varchar
}

Table Project {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
}

Table Task {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
  status varchar
  project_id int [ref: > Project.id]
}

Table Subtask {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
  status varchar
  task_id int [ref: > Task.id]
}

    User "1" -- "0..*" Task
    Task "1" -- "0..*" Subtask
    Project "1" -- "0..*" Task
    ```
