require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

DEPENDS += "bc-native dtc-native gnutls-native python3-pyelftools-native"

DEPENDS:append:riscv64 = " opensbi"

SRCREV = "127a42c7257a6ffbbd1575ed1cbaa8f5408a44b3"

# Fixes 'file could not be found' from oe-core's u-boot config fragments
# https://lists.openembedded.org/g/openembedded-core/topic/117105622
SRC_URI_RISCV = ""

SRC_URI:append:riscv64 = " \
    file://0005-riscv-dts-Add-few-PMU-events.patch \
"

# Only add opensbi dependency if opensbi is in image deps
do_compile[depends] += "opensbi:do_deploy"

do_compile:prepend:riscv64() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

COMPATIBLE_MACHINE = "(freedom-u540|qemuriscv64|unmatched)"
