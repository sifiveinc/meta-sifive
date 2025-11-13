SUMMARY = "To generate vfat image including kernel, DTB and extlinux.conf"
DESCRIPTION = "Recipe to generate boot.vfat image containing kernel, DTB and extlinux.conf that can be flashed to HiFive Premier P550 board using fastboot tool"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


FILESEXTRAPATHS:prepend := "${THISDIR}/../../files/wic:"
SRC_URI = "file://${MACHINE}-emmc-extlinux.conf"

S = "${UNPACKDIR}"
B = "${WORKDIR}/build"

inherit deploy nopackages

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "mtools-native"
do_compile[depends] = "virtual/kernel:do_deploy"

# Size of boot.vfat in MiB
BOOT_VFAT_SIZE ?= "512"
BOOT_VFAT = "boot.vfat"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile() {
	rm -f ${B}/${BOOT_VFAT}
	dd if=/dev/zero of=${B}/${BOOT_VFAT} bs=1M count=${BOOT_VFAT_SIZE}
	mformat -Fi ${B}/${BOOT_VFAT} ::
	mcopy -i ${B}/${BOOT_VFAT} ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} ::/${KERNEL_IMAGETYPE}
	mcopy -i ${B}/${BOOT_VFAT} ${DEPLOY_DIR_IMAGE}/$(basename ${KERNEL_DEVICETREE}) ::/$(basename ${KERNEL_DEVICETREE})
	mmd -i ${B}/${BOOT_VFAT} ::/extlinux
	mcopy -i ${B}/${BOOT_VFAT} ${UNPACKDIR}/${MACHINE}-emmc-extlinux.conf ::/extlinux/extlinux.conf
}

do_deploy() {
	install -m 0644 ${B}/${BOOT_VFAT} ${DEPLOYDIR}/${BOOT_VFAT}
}

addtask deploy after do_compile before do_build

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
