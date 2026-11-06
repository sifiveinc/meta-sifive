SUMMARY = "nsign is an image creation and signature tool."
DESCRIPTION = "It is used to create bootchain image from individual components (ddr_fw.bin, second_boot_fw.bin, fw_payload.bin)."

LICENSE = "CLOSED"

BRANCH = "dev"
SRC_URI = "git://github.com/eswincomputing/Esbd-77serial-nsign.git;branch=${BRANCH};protocol=https \
           file://0001-Changes-in-CMakeLists.txt.patch"

SRCREV = "0ec1fc15df7677ec70506cc3ca81c636a335c308"

B = "${UNPACKDIR}/build"

inherit cmake

BBCLASSEXTEND = "native"
