# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2021.2.2\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2021.2.2\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "C:\NCKU\coding\template bubblesort"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\NCKU\coding\template bubblesort\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/bubblesort.dir/depend.make
# Include the progress variables for this target.
include CMakeFiles/bubblesort.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/bubblesort.dir/flags.make

CMakeFiles/bubblesort.dir/main.c.obj: CMakeFiles/bubblesort.dir/flags.make
CMakeFiles/bubblesort.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\NCKU\coding\template bubblesort\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/bubblesort.dir/main.c.obj"
	C:\PROGRA~1\mingw-w64\x86_64-8.1.0-posix-seh-rt_v6-rev0\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\bubblesort.dir\main.c.obj -c "C:\NCKU\coding\template bubblesort\main.c"

CMakeFiles/bubblesort.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/bubblesort.dir/main.c.i"
	C:\PROGRA~1\mingw-w64\x86_64-8.1.0-posix-seh-rt_v6-rev0\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E "C:\NCKU\coding\template bubblesort\main.c" > CMakeFiles\bubblesort.dir\main.c.i

CMakeFiles/bubblesort.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/bubblesort.dir/main.c.s"
	C:\PROGRA~1\mingw-w64\x86_64-8.1.0-posix-seh-rt_v6-rev0\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S "C:\NCKU\coding\template bubblesort\main.c" -o CMakeFiles\bubblesort.dir\main.c.s

# Object files for target bubblesort
bubblesort_OBJECTS = \
"CMakeFiles/bubblesort.dir/main.c.obj"

# External object files for target bubblesort
bubblesort_EXTERNAL_OBJECTS =

bubblesort.exe: CMakeFiles/bubblesort.dir/main.c.obj
bubblesort.exe: CMakeFiles/bubblesort.dir/build.make
bubblesort.exe: CMakeFiles/bubblesort.dir/linklibs.rsp
bubblesort.exe: CMakeFiles/bubblesort.dir/objects1.rsp
bubblesort.exe: CMakeFiles/bubblesort.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="C:\NCKU\coding\template bubblesort\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable bubblesort.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\bubblesort.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/bubblesort.dir/build: bubblesort.exe
.PHONY : CMakeFiles/bubblesort.dir/build

CMakeFiles/bubblesort.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\bubblesort.dir\cmake_clean.cmake
.PHONY : CMakeFiles/bubblesort.dir/clean

CMakeFiles/bubblesort.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "C:\NCKU\coding\template bubblesort" "C:\NCKU\coding\template bubblesort" "C:\NCKU\coding\template bubblesort\cmake-build-debug" "C:\NCKU\coding\template bubblesort\cmake-build-debug" "C:\NCKU\coding\template bubblesort\cmake-build-debug\CMakeFiles\bubblesort.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/bubblesort.dir/depend

