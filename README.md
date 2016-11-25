# cb2
[![Build Status](https://travis-ci.com/simonsmiley/cb2.svg?token=2QL4U6DTFcpMYPyy2Wyx&branch=master)](https://travis-ci.com/simonsmiley/cb2)
[![codecov](https://codecov.io/gh/simonsmiley/cb2/branch/master/graph/badge.svg?token=cyKPtgOi8R)](https://codecov.io/gh/simonsmiley/cb2)


## Code
`code/cb2` contains an eclipse project that is managed using gradle.

To execute the tests, cd into the project root and run `gradle check`.

This will generate a html report with the results in `build/reports/tests/`

## Repository
The `master` branch is protected, meaning no (force) pushes are allowed to it. Everything has to merged using Pull Requests.

Pull requests have to be reviewed by at least one other person, before they can be merged.

## Testing

For invalid programs, where the errors should be reported on a specific line,
correct behaviour can be enforced by prepending the file names with 3 digits
representing the error line. All of these files are valid names:
* `001myFileName.m`
* `100.myFileName.m`

Any other separator can be used. I propose using a `.` so we can use the file
names for other debugging purposes later on in case we need it...

## Build reports

Build reports are uploaded [here](https://simonsmiley.github.io/cb2)
