package zw.co.quantum.legend.model

import zw.co.quantum.legend.auth.User;

class SettingsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [settingsInstanceList: Settings.list(params), settingsInstanceTotal: Settings.count()]
    }

    def create = {
        def settingsInstance = new Settings()
        settingsInstance.properties = params
        return [settingsInstance: settingsInstance]
    }

    def save = {
        def settingsInstance = new Settings(params)
		
		settingsInstance.institution =  User.get(springSecurityService.principal.id).branch.institution
		
        if (settingsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'settings.label', default: 'Settings'), settingsInstance.id])}"
            redirect(action: "show", id: settingsInstance.id)
        }
        else {
            render(view: "create", model: [settingsInstance: settingsInstance])
        }
    }

    def show = {
        def settingsInstance = Settings.get(params.id)
        if (!settingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
            redirect(action: "list")
        }
        else {
            [settingsInstance: settingsInstance]
        }
    }

    def edit = {
        def settingsInstance = Settings.get(params.id)
        if (!settingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [settingsInstance: settingsInstance]
        }
    }

    def update = {
        def settingsInstance = Settings.get(params.id)
        if (settingsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (settingsInstance.version > version) {
                    
                    settingsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'settings.label', default: 'Settings')] as Object[], "Another user has updated this Settings while you were editing")
                    render(view: "edit", model: [settingsInstance: settingsInstance])
                    return
                }
            }
            settingsInstance.properties = params
            if (!settingsInstance.hasErrors() && settingsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'settings.label', default: 'Settings'), settingsInstance.id])}"
                redirect(action: "show", id: settingsInstance.id)
            }
            else {
                render(view: "edit", model: [settingsInstance: settingsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def settingsInstance = Settings.get(params.id)
        if (settingsInstance) {
            try {
                settingsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
            redirect(action: "list")
        }
    }
	
	
	def list_m = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[settingsInstanceList: Settings.list(params), settingsInstanceTotal: Settings.count()]
	}

	def create_m = {
		def settingsInstance = new Settings()
		settingsInstance.properties = params
		return [settingsInstance: settingsInstance]
	}

	def save_m = {
		def settingsInstance = new Settings(params)
		if (settingsInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'settings.label', default: 'Settings'), settingsInstance.id])}"
			redirect(action: "show_m", id: settingsInstance.id)
		}
		else {
			render(view: "create_m", model: [settingsInstance: settingsInstance])
		}
	}

	def show_m = {
		def settingsInstance = Settings.get(params.id)
		if (!settingsInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
			redirect(action: "list_m")
		}
		else {
			[settingsInstance: settingsInstance]
		}
	}

	def edit_m = {
		def settingsInstance = Settings.get(params.id)
		if (!settingsInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
			redirect(action: "list_m")
		}
		else {
			return [settingsInstance: settingsInstance]
		}
	}

	def update_m = {
		def settingsInstance = Settings.get(params.id)
		if (settingsInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (settingsInstance.version > version) {
					
					settingsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'settings.label', default: 'Settings')] as Object[], "Another user has updated this Settings while you were editing")
					render(view: "edit_m", model: [settingsInstance: settingsInstance])
					return
				}
			}
			settingsInstance.properties = params
			if (!settingsInstance.hasErrors() && settingsInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'settings.label', default: 'Settings'), settingsInstance.id])}"
				redirect(action: "show_m", id: settingsInstance.id)
			}
			else {
				render(view: "edit_m", model: [settingsInstance: settingsInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete_m = {
		def settingsInstance = Settings.get(params.id)
		if (settingsInstance) {
			try {
				settingsInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
				redirect(action: "list_m")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
				redirect(action: "show_m", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'settings.label', default: 'Settings'), params.id])}"
			redirect(action: "list_m")
		}
	}

}
