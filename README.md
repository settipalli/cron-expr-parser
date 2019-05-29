# CRON Expression Parser

A command line application written in Java that parses a cron string and expands each field to show the times at which
it will run.

Only the standard cron format with five time fields (minute, hour, day of month, month, and day of week) plus a command
are considered. The program does not handle the special time strings such as "@yearly".

# Build and Run

- Make sure that your system has JDK version 12 installed. If not, follow the steps outlined at
https://adoptopenjdk.net/ to install the right version for your platform.

- Make sure that Apache Maven is installed. If not, follow the steps outlined at https://maven.apache.org/install.html
to install the right version for your platform.

- If Git isn't installed on your machine, follow the steps listed at
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git to install and configure Git.

- Clone the repository `git@github.com:settipalli/corn-expr-parser.git corn-expr-parser`.
    
        $ git clone git@github.com:settipalli/corn-expr-parser.git
        
> To learn more about cloning a repository, read https://help.github.com/en/articles/cloning-a-repository.

- Navigate to the folder where the repository contents are cloned.

        $ cd corn-expr-parser
        
- Build the `fat jar` of the application.

        $ mvn package
        
- Locate the generated `far jar` and execute the application with the right command line arguments.

        $ java -jar ./target/cron-expr-parser-0.9-jar-with-dependencies.jar */15 0 1,15 * 1-5 /bin/ls -a

