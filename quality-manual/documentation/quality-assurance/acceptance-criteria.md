# Acceptance Criteria

## Introduction

This document describes the final validation and verification stage. This acceptance criteria specification primarily focuses on
the user and release testing phase of the project, rather than the development testing that is described in the test specification
document.

The two main procedures to be considered in ensuring the acceptance of the complete product will be:
- Validation: ensuring that all components of the program satisfy the *user* requirements.
- Verification: ensuring that all components of the program satisfy the *functional* and *non-functional* requirements.

## Test Environment

For this final testing stage, the test environment shall be made as 'realistic' as possible. That is, unless debugging,
the tests shall be carried out in a program environment that is similar to the environment in which the client will use the program.
These program environments are specified in the non-functional requirements (see supported systems in the non-functional requirements
document). This will essentially cover user and release testing, wherein the program shall be used by the team as if they
were a user.

In particular, the system should be tested on each of the three main operating systems as identified in the non-functional requirements.

## Acceptance Tests

The acceptance tests shall involve using the program with the client-provided data files (using various combinations of ant-brains
and ant-worlds). The system should be 'stress-tested', meaning:
- Non-conventional behaviour - experimenting with the GUI performing relatively uncommon actions (e.g. running a game without specifying an ant-brain, specifying no teams in a tournament, etc.) to check that the program does not fall into an inconsistent state.
- Large tasks - for example, testing the system with large ant brain and world files.

In addition to the above, the user requirements shall be individually checked against the program by each member of the team. The
non-functional requirements should also be checked by each member of the team, from a user's perspective.
