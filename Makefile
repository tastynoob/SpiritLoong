all: sbt gen exe wave

OBJ_CPP_PATH:=./
OBJ_CPP_SIM_PRI:=sim_main
OBJ_SV_PATH:=./generated/
OBJ_SV_FILE_PRI:=test
TAR_PATH:=./src/scala/
GEN_DIR:=./obj_dir/
WALL:=

ifdef WARNING
	WALL:=-wall
endif

sbt:
	sbt run
gen:
	verilator \
	-y ${OBJ_SV_PATH} \
	--cc ${WALL} --exe ${OBJ_CPP_PATH}${OBJ_CPP_SIM_PRI}.cpp ${OBJ_SV_PATH}${OBJ_SV_FILE_PRI}.sv \
	--top-module ${OBJ_SV_FILE_PRI} \
	--trace
	make -C ${GEN_DIR} -f V${OBJ_SV_FILE_PRI}.mk V${OBJ_SV_FILE_PRI}
exe:
	${GEN_DIR}V${OBJ_SV_FILE_PRI}
wave:
	gtkwave wave.vcd

.PHONY:clean
clean:
	rm -rf ${GEN_DIR}
