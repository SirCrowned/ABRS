# ABRS #

Agent-based reporting system


## Project setup for developers ##
non-obvious steps:

### Importing codestyle (Intellij) ###

1. copy `ABRS-JetBrains_codestyle.xml` file from project root dir to  
  `~/.IntelliJIdeaXX/config/codestyles` (Linux),  
  `~/Library/Preferences/IntelliJIdeaXX/codestyles` (OS X),  
  or `<User home>\.IntelliJIdeaXX\config\codestyles` (Windows)
1. Restart Intellij
1. in IDE: File → Settings → Code Style → Manage →  
  choose imported style, choose "Copy to Project" and apply changes
  
### Git ###
#### Ignore file permissions changes ####
```shell
$ git config core.fileMode false
```