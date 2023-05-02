```mermaid
erDiagram
    PROJECT }|..|{ TASK : contains
    TASK ||--|{ SUBTASK : contains
    TASK }--||{ USER : assigned to
    TASK }--||{ STATUS : has
    TASK }--||{ PRIORITY : has
    USER ||--|{ ROLE : has
    ROLE }--|{ PERMISSION : has
```
