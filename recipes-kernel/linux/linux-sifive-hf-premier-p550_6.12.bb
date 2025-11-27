DESCRIPTION = "SiFive HiFive Premier P550 Board Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

KBRANCH ?= "rel/kernel-6.12/hifive-premier-p550"
KBRANCH:hifive-premier-p550 = "rel/kernel-6.12/hifive-premier-p550"

SRCREV_machine ?= "3340c5ae8c9d093fdf23ed8c68437846cac893b6"
SRCREV_machine:hifive-premier-p550 = "3340c5ae8c9d093fdf23ed8c68437846cac893b6"
SRCREV_meta ?= "bc26c6c6b91fa0e4de4920544cc4aeeb3dedd894"

KCONFIG_MODE = "--alldefconfig"

KBUILD_DEFCONFIG ?= "hifive-premier-p550_defconfig"
KBUILD_DEFCONFIG:hifive-premier-p550 ?= "hifive-premier-p550_defconfig"

KERNEL_EXTRA_FEATURES ?= ""
KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
KERNEL_FEATURES:remove = "features/debug/printk.scc"
KERNEL_FEATURES:remove = "features/kernel-sample/kernel-sample.scc"

require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://git@github.com/sifive/riscv-linux.git;protocol=ssh;name=machine;branch=${KBRANCH} \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-6.12;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "6.12.33"
LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"

INSANE_SKIP:kernel-vmlinux = "textrel"

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
