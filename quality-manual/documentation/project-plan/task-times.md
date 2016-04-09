# Task Times

This document gives an approximate optimistic-realistic-pessimistic estimation for the time spent on each task in the project. All times are given in days.

| Task                     | Optimistic | Realistic | Pessimistic |
| ------------------------ | ---------- | --------- | ----------- |
| __Preparation__          |            |           |             |
| Team Organisation        | 1          | 2         | 3           |  
| Configuration Management | 2          | 3         | 5           |
| __Plan__                 |            |           |             |
| Conflict Resolution      | 1          | 2         | 3           |
| Phase Plan               | 4          | 7         | 10          |
| Organisation Plan        | 3          | 5         | 7           |
| Peer Assessment Plan     | 1          | 2         | 3           |
| __Protocols__            |            |           |             |
| Acceptance Criteria      | 2          | 3         | 4           |
| Development Protocols    | 1          | 2         | 3           |
| Test Specification       | 4          | 5         | 7           |
| __Requirements__         |            |           |             |
| Functional               | 5          | 8         | 11          |
| Non-Functional           | 3          | 6         | 9           |
| Domain                   | 1          | 2         | 3           |
| __Design__               |            |           |             |
| High -> Low-Level        | 20         | 22        | 24          |
| __Development__          |            |           |             |
| Programming              | 20         | 25        | 30          |
| Documentation            | 5          | 7         | 9           |
| Unit Testing             | 10         | 12        | 14          |
| __Validation__           |            |           |             |
| Component Testing        | 7          | 9         | 11          |
| __Other__                |            |           |             |
| User Documentation       | 7          | 9         | 11          |
| Report                   | 2          | 3         | 4           |

## PERT Chart

The PERT chart below shows the main phases of the project. It is assumed that the subtasks of each phase (as in the above table) can be worked on asynchronously by the team, so the longest sub-task for each phase has been used to characterise the total completion time of each phase. In the chart the `(x, y, z)` tuple below each node is the optimistic, realistic and pessimistic completion time in days. The realistic completion time has been used to calculate the critical path (the path marked with red arrows).

![pert-chart](pert.png)

The project was started by the group on 18/02/2016. This means, assuming each phase on the critical path is completed in exactly the above 'realistic' number of days, the completion date of the project should be 28/05/2016. This gives 8 days of freedom in the critical path before the project deadline.

## Milestones

From the above PERT chart, milestone goals are:

| Task                              | Date               |
| ----------------------------------|--------------------|
| Preparation Complete              | 28/02/2016         |
| Plan Complete                     | 07/03/2016         |
| Requirements Complete             | 07/03/2016         |
| Design Complete                   | 29/03/2016         |
| Protocols Complete                | 29/03/2016         |
| Development Complete              | 23/04/2016         |
| Validation Complete               | 02/05/2016         |
| User Documentation Complete       | 02/05/2016         |
| Report/Finalisation Complete      | 05/05/2016         |
